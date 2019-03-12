/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.plugin.builder;

import com.github.fartherp.plugin.model.GeneratorModuleType;
import com.github.fartherp.plugin.wizard.GeneratorModuleWizardStep;
import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ModifiableRootModel;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/3/8
 */
public class GeneratorModuleBuilder extends ModuleBuilder {

    @Override
    public void setupRootModel(ModifiableRootModel model) throws ConfigurationException {

    }

    @Override
    public ModuleType getModuleType() {
        return GeneratorModuleType.getInstance();
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new GeneratorModuleWizardStep(context.getProject());
    }
}
