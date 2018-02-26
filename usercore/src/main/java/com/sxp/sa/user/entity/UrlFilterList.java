package com.sxp.sa.user.entity;

import com.sxp.sa.user.repository.ResourceRepository;
import com.sxp.sa.user.repository.RoleResourceRepository;
import com.sxp.sa.user.repository.UrlFilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class UrlFilterList {

    @Autowired
    private UrlFilterRepository urlFilterRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    private   Map<String ,UrlFilter> filterList = new HashMap<String,UrlFilter>();




     @PostConstruct
     public void  initFilterList(){


         //清空
         this.filterList.clear();
        //获取所有的资源
         List<Resource> resourceList = resourceRepository.findAll();

         for(int i = 0 ;i<resourceList.size();i++){

             UrlFilter urlFilter = new UrlFilter();
             urlFilter.setUrl(resourceList.get(i).getUrl());

             String rolesStr = "";
             //可访问该资源的角色
             List<Role> roles = roleResourceRepository.findByResourceId(resourceList.get(i).getId());
             for(int k = 0 ;k<roles.size();k++){
                 rolesStr+=roles.get(k).getRole();

                 if(k<roles.size()-1){
                     rolesStr+=",";
                 }
             }
             urlFilter.setRoles(rolesStr);

             filterList.put(urlFilter.getUrl(),urlFilter);
         }


     }







    public UrlFilterRepository getUrlFilterRepository() {
        return urlFilterRepository;
    }

    public void setUrlFilterRepository(UrlFilterRepository urlFilterRepository) {
        this.urlFilterRepository = urlFilterRepository;
    }

    public Map<String, UrlFilter> getFilterList() {
        return filterList;
    }

    public void setFilterList(Map<String, UrlFilter> filterList) {
        this.filterList = filterList;
    }
}
