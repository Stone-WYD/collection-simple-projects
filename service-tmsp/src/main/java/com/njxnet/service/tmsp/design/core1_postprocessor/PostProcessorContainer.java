package com.njxnet.service.tmsp.design.core1_postprocessor;

import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.util.ApplicationContextUtil;

import java.util.Comparator;
import java.util.List;

public class PostProcessorContainer<T extends BasePostProcessor<E>, E>{

    private List<T> postProcessors;

    private PostProcessorContainer() {
    }

    public static <T extends BasePostProcessor<E>, E> PostProcessorContainer<T, E> getInstance(Class<T> monitorPostProcessorClass){
        PostProcessorContainer<T, E> postProcessorContainer = new PostProcessorContainer<>();
        postProcessorContainer.postProcessors = ApplicationContextUtil.getBeansOfType(monitorPostProcessorClass);
        if (CollectionUtil.isNotEmpty(postProcessorContainer.postProcessors)) {
            postProcessorContainer.postProcessors.sort(Comparator.comparing(BasePostProcessor::getPriprity));
        }
        // 使用者无法 new 对象，只能通过该方法获取实例，给方法扩展留空间
        return postProcessorContainer;
    }

    public boolean handleBefore(E postContext){

        if (CollectionUtil.isNotEmpty(postProcessors)) {
            for (T postProcessor : postProcessors) {
                // 如果支持处理，才会处理
                if (postProcessor.support(postContext)) {
                    postProcessor.handleBefore(postContext);
                }
            }
        }


        return false; // 有操作则返回false
    }

    public void handleAfter(E postContext){
        if (CollectionUtil.isNotEmpty(postProcessors)) {
            for (T postProcessor : postProcessors) {
                // 如果支持处理，才会处理
                if (postProcessor.support(postContext)) {
                    postProcessor.handleAfter(postContext);
                }
            }
        }
    }
}
