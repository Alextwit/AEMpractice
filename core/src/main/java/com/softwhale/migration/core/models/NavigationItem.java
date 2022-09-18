package com.softwhale.migration.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class NavigationItem {

    @ValueMapValue
    private String navTitle;

    @ValueMapValue
    private String navPath;

    public String getNavTitle() {
        return navTitle;
    }

    public String getNavPath() {
        return navPath;
    }
}
