/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.plugin.wizard;

import com.github.fartherp.plugin.form.CodeSettingsForm;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/3/8
 */
public class GeneratorModuleWizardStep extends ModuleWizardStep {

    private final Project project;

    public GeneratorModuleWizardStep(Project project) {
        this.project = project;
    }

    @Override
    public JComponent getComponent() {
        CodeSettingsForm contentPanel = new CodeSettingsForm();
        contentPanel.createUI(project);
        return contentPanel.getContentPane();
    }

    @Override
    public void updateDataModel() {
        //todo update model according to UI
    }
}
