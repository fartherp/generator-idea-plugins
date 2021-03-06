/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.plugin.action;

import com.github.fartherp.codegenerator.config.CodeGenContext;
import com.github.fartherp.plugin.adapter.NewGeneratorAdapterFactory;
import com.github.fartherp.plugin.config.CodeConfig;
import com.github.fartherp.plugin.wrapper.CodeGenerateDialogWrapper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.github.fartherp.plugin.utils.ValidateUtils.validateConfig;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/3/11
 */
public class CodeGenerateDialogAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        CodeGenerateDialogWrapper wrapper = new CodeGenerateDialogWrapper(project);
        boolean result = wrapper.showAndGet();
        if (result) {
            CodeConfig config = CodeConfig.getInstance(project);
            if (!validateConfig(project, config)) {
                return;
            }

            if (StringUtils.isBlank(config.getProjectName())) {
                Messages.showWarningDialog(project, "项目名为空.", "Warning");
                return;
            }

            if (StringUtils.isBlank(config.getClasspath())) {
                Messages.showWarningDialog(project, "类路径为空.", "Warning");
                return;
            }

            if (StringUtils.isBlank(config.getGeneratorType())) {
                Messages.showWarningDialog(project, "项目架构为空.", "Warning");
                return;
            }

            if (CollectionUtils.isEmpty(config.getTableNames())) {
                Messages.showWarningDialog(project, "没有选择表.", "Warning");
                return;
            }

            CodeGenContext context = new CodeGenContext();
            context.setClasspath(config.getClasspath());
            context.setProjectName(config.getProjectName());
            context.setGeneratorType(config.getGeneratorType());
            context.setUser(config.getUser());
            context.setPassword(config.getPassword());
            context.setUrl(config.getUrl());
            context.setDatabaseByUrl(config.getUrl());
            context.setIsColumnNameDelimited(1);
            context.setTableNameList(new ArrayList<>(config.getTableNames()));

            NewGeneratorAdapterFactory.create(config.getGeneratorType(), config.getPojoPath(),
                    config.getDaoPath(), config.getServicePath(), config.getXmlPath(), context).generateAdapter();
        }
    }
}
