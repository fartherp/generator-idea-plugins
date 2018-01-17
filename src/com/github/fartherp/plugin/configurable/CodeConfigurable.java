/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.plugin.configurable;

import com.github.fartherp.plugin.form.CodeSettingsForm;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/15
 */
public class CodeConfigurable implements SearchableConfigurable {

    private CodeSettingsForm contentPanel;

    @SuppressWarnings("FieldCanBeLocal")
    private final Project project;

    public CodeConfigurable(Project project) {
        this.project = project;
    }

    @NotNull
    public String getId() {
        return "preference.CodeConfigurable";
    }

    @Nls
    public String getDisplayName() {
        return "Mybatis generator.";
    }

    @Nullable
    public JComponent createComponent() {
        contentPanel = new CodeSettingsForm();
        contentPanel.createUI(project);
        return contentPanel.getContentPane();
    }

    public boolean isModified() {
        return contentPanel.isModified();
    }

    public void apply() throws ConfigurationException {
        contentPanel.apply();
    }

    public void reset() {
        contentPanel.reset();
    }

    public void disposeUIResources() {
        contentPanel = null;
    }
}
