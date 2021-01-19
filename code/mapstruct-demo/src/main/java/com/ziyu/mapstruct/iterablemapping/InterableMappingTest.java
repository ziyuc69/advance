package com.ziyu.mapstruct.iterablemapping;

import org.assertj.core.util.Lists;

/**
 * @author ziyu
 */
public class InterableMappingTest {

    public static void main(String[] args) {
        SourceTargetMapper instance = SourceTargetMapper.INSTANCE;
        Source source = new Source();
        source.setMyStrings(Lists.newArrayList("1", "2", "3", "4"));
        Target target = instance.toTarget(source);
        System.out.println(target.getTargets());
    }
}
