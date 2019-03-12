/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.plugin.form;

import com.github.fartherp.codegenerator.api.Generator;
import com.github.fartherp.codegenerator.factory.GeneratorFactory;
import com.github.fartherp.framework.common.extension.ExtensionLoader;
import com.github.fartherp.plugin.config.CodeConfig;
import com.github.fartherp.plugin.dao.CodeDao;
import com.github.fartherp.plugin.ui.UiComponentFacade;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.ObjectUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.fartherp.plugin.utils.ValidateUtils.validateConfig;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/15
 */
public class CodeSettingsForm extends JDialog {
    private JPanel contentPane;
    private JTextField user;
    private JPasswordField password;
    private JTextField projectName;
    private JTextField classpath;
    private JButton selectButton;
    private JComboBox generatorType;
    private JTable table1;
    private JButton testButton;
    private JTextField database;
    private JTextField host;
    private JTextField port;
    private JTextField pojoPath;
    private JButton pojoButton;
    private JTextField daoPath;
    private JButton daoButton;
    private JButton serviceButton;
    private JTextField servicePath;
    private JTextField xmlPath;
    private JButton xmlButton;
    private CodeConfig config;

    public CodeSettingsForm() {
        setContentPane(contentPane);
    }

    public void createUI(Project project) {
        config = CodeConfig.getInstance(project != null ? project : ProjectManager.getInstance().getDefaultProject());
        this.user.setText(config.getUser());
        this.password.setText(config.getPassword());
        this.projectName.setText(config.getProjectName());
        this.classpath.setText(config.getClasspath());
        Set<String> set = ExtensionLoader.getExtensionLoader(Generator.class, GeneratorFactory.PATH).getSupportedExtensions();
        for (String s : set) {
            this.generatorType.addItem(s);
        }
        config.setTableNames(new HashSet<String>());
        this.database.setText(config.getDatabase());
        this.host.setText(config.getHost());
        this.port.setText(config.getPort());
        this.pojoPath.setText(config.getPojoPath());
        this.daoPath.setText(config.getDaoPath());
        this.servicePath.setText(config.getServicePath());
        this.xmlPath.setText(config.getXmlPath());
        this.selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apply();
                if (!validateConfig(project, config)) {
                    return;
                }
                config.setUrl(String.format("jdbc:mysql://%s:%s/%s", config.getHost(), config.getPort(), config.getDatabase()));
                String path = project.getBasePath();
                if (config.getPojoPath() == null) {
                    config.setPojoPath(path);
                }
                if (config.getDaoPath() == null) {
                    config.setDaoPath(path);
                }
                if (config.getServicePath() == null) {
                    config.setServicePath(path);
                }
                if (config.getXmlPath() == null) {
                    config.setXmlPath(path);
                }
                apply();
                // 点击查询按钮事件
                String[] title = {"", "表名"};
                try {
                    List<String> list = CodeDao.selectTableName(config);
                    Object[][] data = new Object[list.size()][2];
                    for (int i = 0; i < list.size(); i++) {
                        data[i][1] = list.get(i);
                    }
                    table1.setModel(new DefaultTableModel(data, title));
                    TableColumn tc = table1.getColumnModel().getColumn(0);
                    tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));
                    tc.setCellEditor(table1.getDefaultEditor(Boolean.class));
                    tc.setCellRenderer(table1.getDefaultRenderer(Boolean.class));
                    tc.setMaxWidth(100);
                } catch (Exception ex) {
                    Messages.showWarningDialog(project, "数据库连接错误,请检查配置.", "Warning");
                }
            }
        });
        this.table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int rowIndex = table1.rowAtPoint(e.getPoint()); //获取点击的行
                    Boolean flag = (Boolean) table1.getValueAt(rowIndex, 0);
                    Set<String> tableNames = CodeSettingsForm.this.config.getTableNames();
                    if (flag != null && flag) {
                        tableNames.add(table1.getValueAt(rowIndex, 1).toString());
                    } else {
                        tableNames.remove(table1.getValueAt(rowIndex, 1).toString());
                    }
                }
            }
        });

        this.testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apply();
                if (!validateConfig(project, config)) {
                    return;
                }
                config.setUrl(String.format("jdbc:mysql://%s:%s/%s", config.getHost(), config.getPort(), config.getDatabase()));
                apply();
                try {
                    String mysqlVerison = CodeDao.testDatabase(config);
                    Messages.showInfoMessage(project, "Connection successful! \r\nMySQL version : " + mysqlVerison, "OK");
                } catch (Exception ex) {
                    Messages.showWarningDialog(project, "数据库连接错误,请检查配置.", "Warning");
                }
            }
        });

        this.pojoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiComponentFacade uiComponentFacade = UiComponentFacade.getInstance(project);
                VirtualFile baseDir = project.getBaseDir();
                VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择pojo生成目录", baseDir, baseDir);
                if (null != vf) {
                    CodeSettingsForm.this.pojoPath.setText(vf.getPath());
                }
            }
        });

        this.daoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiComponentFacade uiComponentFacade = UiComponentFacade.getInstance(project);
                VirtualFile baseDir = project.getBaseDir();
                VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择dao生成目录", baseDir, baseDir);
                if (null != vf) {
                    CodeSettingsForm.this.daoPath.setText(vf.getPath());
                }
            }
        });

        this.serviceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiComponentFacade uiComponentFacade = UiComponentFacade.getInstance(project);
                VirtualFile baseDir = project.getBaseDir();
                VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择service生成目录", baseDir, baseDir);
                if (null != vf) {
                    CodeSettingsForm.this.servicePath.setText(vf.getPath());
                }
            }
        });

        this.xmlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiComponentFacade uiComponentFacade = UiComponentFacade.getInstance(project);
                VirtualFile baseDir = project.getBaseDir();
                VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择xml生成目录", baseDir, baseDir);
                if (null != vf) {
                    CodeSettingsForm.this.xmlPath.setText(vf.getPath());
                }
            }
        });
    }

    public boolean isModified() {
        boolean modified = false;
        modified |= !this.user.getText().equals(config.getUser());
        modified |= !new String(this.password.getPassword()).equals(config.getPassword());
        modified |= !this.projectName.getText().equals(config.getProjectName());
        modified |= !this.classpath.getText().equals(config.getClasspath());
        modified |= !(this.generatorType.getSelectedItem() != null && this.generatorType.getSelectedItem().toString().equals(config.getGeneratorType()));
        modified |= !this.database.getText().equals(config.getDatabase());
        modified |= !this.host.getText().equals(config.getHost());
        modified |= !this.port.getText().equals(config.getPort());
        modified |= !this.pojoPath.getText().equals(config.getPojoPath());
        modified |= !this.daoPath.getText().equals(config.getDaoPath());
        modified |= !this.servicePath.getText().equals(config.getServicePath());
        modified |= !this.xmlPath.getText().equals(config.getXmlPath());
        return modified;
    }

    public void apply() {
        config.setUser(this.user.getText());
        config.setPassword(new String(this.password.getPassword()));
        config.setProjectName(this.projectName.getText());
        config.setClasspath(this.classpath.getText());
        config.setGeneratorType(ObjectUtils.toString(this.generatorType.getSelectedItem()));
        config.setDatabase(this.database.getText());
        config.setHost(this.host.getText());
        config.setPort(this.port.getText() != null ? this.port.getText() : "3306");
        config.setPojoPath(this.pojoPath.getText());
        config.setDaoPath(this.daoPath.getText());
        config.setServicePath(this.servicePath.getText());
        config.setXmlPath(this.xmlPath.getText());
    }

    public void reset() {
        this.user.setText(config.getUser());
        this.password.setText(config.getPassword());
        this.projectName.setText(config.getProjectName());
        this.classpath.setText(config.getClasspath());
        this.generatorType.setSelectedItem(config.getGeneratorType());
        this.database.setText(config.getDatabase());
        this.host.setText(config.getHost());
        this.port.setText(config.getPort());
        this.pojoPath.setText(config.getPojoPath());
        this.daoPath.setText(config.getDaoPath());
        this.servicePath.setText(config.getServicePath());
        this.xmlPath.setText(config.getXmlPath());
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
