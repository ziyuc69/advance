package com.ziyu.mapstruct.iterablemapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-19T16:14:43+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class SourceTargetMapperImpl implements SourceTargetMapper {

    @Override
    public Target toTarget(Source s) {
        if ( s == null ) {
            return null;
        }

        Target target = new Target();

        List<String> list = s.getMyStrings();
        if ( list != null ) {
            target.setTargets( new ArrayList<String>( list ) );
        }

        return target;
    }
}
