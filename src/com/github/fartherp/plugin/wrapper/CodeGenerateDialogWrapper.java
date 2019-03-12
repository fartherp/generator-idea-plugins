/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.plugin.wrapper;

import com.github.fartherp.plugin.form.CodeSettingsForm;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/3/11
 */
public class CodeGenerateDialogWrapper extends DialogWrapper {

    private final Project project;

    public CodeGenerateDialogWrapper(@Nullable Project project) {
        super(project);
        this.project = project;
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        CodeSettingsForm contentPanel = new CodeSettingsForm();
        contentPanel.createUI(this.project);
        return contentPanel.getContentPane();
    }
}
