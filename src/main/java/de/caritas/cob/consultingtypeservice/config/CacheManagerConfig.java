package de.caritas.cob.consultingtypeservice.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheManagerConfig {

  public static final String TENANT_CACHE = "tenantCache";
  public static final String CONSULTING_TYPE_GROUPS_CACHE = "consultingTypeGroupsCache";

  @Value("${cache.tenant.configuration.maxEntriesLocalHeap}")
  private long tenantMaxEntriesLocalHeap;

  @Value("${cache.tenant.configuration.eternal}")
  private boolean tenantEternal;

  @Value("${cache.tenant.configuration.timeToIdleSeconds}")
  private long tenantTimeToIdleSeconds;

  @Value("${cache.tenant.configuration.timeToLiveSeconds}")
  private long tenantTimeToLiveSeconds;

  @Value("${cache.groups.configuration.maxEntriesLocalHeap}")
  private long groupsMaxEntriesLocalHeap;

  @Value("${cache.groups.configuration.eternal}")
  private boolean groupsEternal;

  @Value("${cache.groups.configuration.timeToIdleSeconds}")
  private long groupsTimeToIdleSeconds;

  @Value("${cache.groups.configuration.timeToLiveSeconds}")
  private long groupsTimeToLiveSeconds;

  @Bean
  public CacheManager cacheManager() {
    return new EhCacheCacheManager(ehCacheManager());
  }

  @Bean(destroyMethod = "shutdown")
  public net.sf.ehcache.CacheManager ehCacheManager() {
    var config = new net.sf.ehcache.config.Configuration();
    config.addCache(buildTenantCacheConfiguration());
    config.addCache(buildConsultingTypeGroupsCacheConfiguration());
    return net.sf.ehcache.CacheManager.newInstance(config);
  }

  private CacheConfiguration buildTenantCacheConfiguration() {
    var tenantCacheConfiguration = new CacheConfiguration();
    tenantCacheConfiguration.setName(TENANT_CACHE);
    tenantCacheConfiguration.setMaxEntriesLocalHeap(tenantMaxEntriesLocalHeap);
    tenantCacheConfiguration.setEternal(tenantEternal);
    tenantCacheConfiguration.setTimeToIdleSeconds(tenantTimeToIdleSeconds);
    tenantCacheConfiguration.setTimeToLiveSeconds(tenantTimeToLiveSeconds);
    return tenantCacheConfiguration;
  }

  private CacheConfiguration buildConsultingTypeGroupsCacheConfiguration() {
    var tenantCacheConfiguration = new CacheConfiguration();
    tenantCacheConfiguration.setName(CONSULTING_TYPE_GROUPS_CACHE);
    tenantCacheConfiguration.setMaxEntriesLocalHeap(groupsMaxEntriesLocalHeap);
    tenantCacheConfiguration.setEternal(groupsEternal);
    tenantCacheConfiguration.setTimeToIdleSeconds(groupsTimeToIdleSeconds);
    tenantCacheConfiguration.setTimeToLiveSeconds(groupsTimeToLiveSeconds);
    return tenantCacheConfiguration;
  }
}
