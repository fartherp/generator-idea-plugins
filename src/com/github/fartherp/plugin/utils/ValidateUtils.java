/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.plugin.utils;

import com.github.fartherp.plugin.config.CodeConfig;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/17
 */
public class ValidateUtils {
    public static boolean validateConfig(Project project, CodeConfig config) {
        if (StringUtils.isBlank(config.getUrl())) {
            Messages.showWarningDialog(project, "URL为空.", "Warning");
            return false;
        }

        if (StringUtils.isBlank(config.getUser())) {
            Messages.showWarningDialog(project, "用户名为空.", "Warning");
            return false;
        }

        if (StringUtils.isBlank(config.getPassword())) {
            Messages.showWarningDialog(project, "密码为空.", "Warning");
            return false;
        }

        return true;
    }
}
