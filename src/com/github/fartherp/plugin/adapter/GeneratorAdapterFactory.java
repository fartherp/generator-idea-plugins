/*
 * Copyright (c) 2018. CK. All rights reserved.
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

    /**
     * 生成对应的适配器
     * @param type 生成类型
     * @param path 相对要生成的路径
     * @param context 全局变量
     * @return GeneratorAdapter
     */
    public static GeneratorAdapter create(String type, String path, CodeGenContext context) {
        if ("os".equals(type)) {
            return new FrameworkGeneratorAdapter(path, context);
        }
        throw new RuntimeException("没有适配类");
    }
}
