/*
 * Copyright (c) 2018. juzhen.io. All rights reserved.
 */

package com.github.fartherp.plugin.adapter;

import com.github.fartherp.codegenerator.config.CodeGenContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/1/16
 */
public class GeneratorAdapterFactory {
    public static GeneratorAdapter create(String type, String path, CodeGenContext context) {
        if ("framework".equals(type)) {
            return new FrameworkGeneratorAdapter(path, context);
        }
        throw new RuntimeException("没有适配类");
    }
}
