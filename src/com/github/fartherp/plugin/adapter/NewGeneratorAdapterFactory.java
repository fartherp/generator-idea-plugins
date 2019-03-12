/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.plugin.adapter;

import com.github.fartherp.codegenerator.config.CodeGenContext;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2019/3/12
 */
public class NewGeneratorAdapterFactory {
    /**
     * 生成对应的适配器
     * @param type 生成类型
     * @param context 全局变量
     * @return GeneratorAdapter
     */
    public static GeneratorAdapter create(String type, String pojoPath, String daoPath, String servicePath, String xmlPath, CodeGenContext context) {
        if ("framework".equals(type)) {
            return new NewFrameworkGeneratorAdapter(pojoPath, daoPath, servicePath, xmlPath, context);
        }
        throw new RuntimeException("没有适配类");
    }
}
