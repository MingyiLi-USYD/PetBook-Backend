package usyd.mingyi.springcloud.component;

import org.mapstruct.Mapper;

@Mapper
public interface GenericMapper<S, D> {
    D mapSourceToDestination(S source);

    S mapDestinationToSource(D destination);
}
