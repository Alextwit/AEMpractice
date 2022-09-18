package com.softwhale.migration.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AemPracticeModel {

//    @OSGiService
//    private SlingSettingsService settingsService;

    @ValueMapValue
    private String imagePath;

    @ValueMapValue
    private boolean backgroundImage;

    @Inject
    @Via("resource")
    private List<NavigationItem> navItems;

    public String getImagePath() {
        return imagePath;
    }

    public boolean isBackgroundImage() {
        return backgroundImage;
    }

    public List<NavigationItem> getNavItems() {
        return navItems;
    }

    // We can use SlingSettingService to check environment run mode
    // in case you don't like my wcmmode approach
//    public Boolean getPublish() {
//        return settingsService.getRunModes().contains(Externalizer.PUBLISH);
//    }
//
//    public Boolean getAuthor() {
//        return settingsService.getRunModes().contains(Externalizer.AUTHOR);
//    }
}
