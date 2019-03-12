/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.plugin.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/3/11
 */
public class UiComponentFacade {

    private final Project project;

    private final FileEditorManager fileEditorManager;

    private UiComponentFacade(Project project) {
        this.project = project;
        this.fileEditorManager = FileEditorManager.getInstance(project);
    }

    public static UiComponentFacade getInstance(@NotNull Project project) {
        return new UiComponentFacade(project);
    }

    public VirtualFile showSingleFolderSelectionDialog(@NotNull String title,
                                                       @Nullable VirtualFile toSelect,
                                                       @Nullable VirtualFile... roots) {
        final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        descriptor.setTitle(title);
        if (null != roots) {
            descriptor.setRoots(roots);
        }
        return FileChooser.chooseFile(descriptor, project, toSelect);
    }
}
