/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.starter.security.limiter.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import top.continew.starter.core.constant.PropertiesConstants;
import top.continew.starter.security.limiter.core.DefaultRateLimiterNameGenerator;
import top.continew.starter.security.limiter.core.RateLimiterNameGenerator;

/**
 * 限流器自动配置
 * 
 * @author KAI
 * @author Charles7c
 * @since 2.2.0
 */
@AutoConfiguration
@EnableConfigurationProperties(RateLimiterProperties.class)
@ComponentScan({"top.continew.starter.security.limiter.core"})
@ConditionalOnProperty(prefix = PropertiesConstants.SECURITY_LIMITER, name = PropertiesConstants.ENABLED, matchIfMissing = true)
public class RateLimiterAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterAutoConfiguration.class);

    /**
     * 限流器名称生成器
     */
    @Bean
    @ConditionalOnMissingBean
    public RateLimiterNameGenerator nameGenerator() {
        return new DefaultRateLimiterNameGenerator();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[ContiNew Starter] - Auto Configuration 'Security-RateLimiter' completed initialization.");
    }
}