/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.plugin.form;

import com.github.fartherp.codegenerator.api.Generator;
import com.github.fartherp.codegenerator.factory.GeneratorFactory;
import com.github.fartherp.framework.common.extension.ExtensionLoader;
import com.github.fartherp.plugin.config.CodeConfig;
import com.github.fartherp.plugin.dao.CodeDao;
import com.github.fartherp.plugin.utils.ValidateUtils;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.ObjectUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
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
    private JTextField url;
    private JTextField user;
    private JPasswordField password;
    private JTextField projectName;
    private JTextField classpath;
    private JButton selectButton;
    private JComboBox generatorType;
    private JTable table1;
    private CodeConfig config;

    public CodeSettingsForm() {
        setContentPane(contentPane);
    }

    public void createUI(Project project) {
        config = CodeConfig.getInstance(project);
        this.url.setText(config.getUrl());
        this.user.setText(config.getUser());
        this.password.setText(config.getPassword());
        this.projectName.setText(config.getProjectName());
        this.classpath.setText(config.getClasspath());
        Set<String> set = ExtensionLoader.getExtensionLoader(Generator.class, GeneratorFactory.PATH).getSupportedExtensions();
        for (String s : set) {
            this.generatorType.addItem(s);
        }
        config.setTableNames(new HashSet<String>());
        this.selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apply();
                if (!validateConfig(project, config)) {
                    return;
                }
                // 点击查询按钮事件
                String title[] = {"", "表名"};
                List<String> list = CodeDao.selectTableName(config);
                Object[][] data = new Object[list.size()][2];
                for (int i = 0; i < list.size(); i++) {
                    data[i][1] = list.get(i);
                }
                DefaultTableModel tableModel = new DefaultTableModel(data, title);
                table1.setModel(tableModel);
                TableColumnModel tcm = table1.getColumnModel();
                tcm.getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
            }
        });
        this.table1.addMouseListener(new MouseAdapter() {
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
    }

    public boolean isModified() {
        boolean modified = false;
        modified |= !this.url.getText().equals(config.getUrl());
        modified |= !this.user.getText().equals(config.getUser());
        modified |= !new String(this.password.getPassword()).equals(config.getPassword());
        modified |= !this.projectName.getText().equals(config.getProjectName());
        modified |= !this.classpath.getText().equals(config.getClasspath());
        modified |= !(this.generatorType.getSelectedItem() != null && this.generatorType.getSelectedItem().toString().equals(config.getGeneratorType()));
        return modified;
    }

    public void apply() {
        config.setUrl(this.url.getText());
        config.setUser(this.user.getText());
        config.setPassword(new String(this.password.getPassword()));
        config.setProjectName(this.projectName.getText());
        config.setClasspath(this.classpath.getText());
        config.setGeneratorType(ObjectUtils.toString(this.generatorType.getSelectedItem()));
    }

    public void reset() {
        this.url.setText(config.getUrl());
        this.user.setText(config.getUser());
        this.password.setText(config.getPassword());
        this.projectName.setText(config.getProjectName());
        this.classpath.setText(config.getClasspath());
        this.generatorType.setSelectedItem(config.getGeneratorType());
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
