package usyd.mingyi.common.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("petbook.mvc")
public class WebConfigProperties {
    private String [] excludes={};

    public String[] getExcludes() {
        return excludes;
    }

    public void setExcludes(String[] excludes) {
        this.excludes = excludes;
    }
}
