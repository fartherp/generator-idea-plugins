/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.plugin.model;

import com.github.fartherp.plugin.builder.GeneratorModuleBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/3/8
 */
public class GeneratorModuleType extends ModuleType<GeneratorModuleBuilder> {
    private static final String ID = "GENERATOR_MODULE_TYPE";

    public GeneratorModuleType() {
        super(ID);
    }

    public static GeneratorModuleType getInstance() {
        return (GeneratorModuleType) ModuleTypeManager.getInstance().findByID(ID);
    }

    @NotNull
    @Override
    public GeneratorModuleBuilder createModuleBuilder() {
        return new GeneratorModuleBuilder();
    }

    @NotNull
    @Override
    public String getName() {
        return "Generator Code";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Generator Code";
    }


    @Override
    public Icon getNodeIcon(@Deprecated boolean b) {
        return AllIcons.General.Information;
    }

    @NotNull
    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext,
                                                @NotNull GeneratorModuleBuilder moduleBuilder,
                                                @NotNull ModulesProvider modulesProvider) {
        return super.createWizardSteps(wizardContext, moduleBuilder, modulesProvider);
    }
}
