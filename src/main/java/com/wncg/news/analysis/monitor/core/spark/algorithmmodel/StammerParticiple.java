package com.wncg.news.analysis.monitor.core.spark.algorithmmodel;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StammerParticiple implements Participle{

    @Override
    public String participle(String originalText) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> textList = segmenter.sentenceProcess(originalText);
        StringBuffer result = new StringBuffer();
        for(String word : textList){
            result.append(word + " ");
        }
        return result.toString().trim();
    }
}
