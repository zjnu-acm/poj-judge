/*
 * Copyright 2017 ZJNU ACM.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.zjnu.acm.judge.service.impl;

import cn.edu.zjnu.acm.judge.config.Constants;
import cn.edu.zjnu.acm.judge.mapper.SystemMapper;
import cn.edu.zjnu.acm.judge.service.SystemService;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhanhb
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService {

    private final SystemMapper systemMapper;

    @Autowired
    public SystemServiceImpl(SystemMapper systemMapper) {
        this.systemMapper = systemMapper;
    }

    @Autowired // ensure flyway initialize before this service
    public void setFlywayMigrationInitializer(FlywayMigrationInitializer flywayMigrationInitializer) {
    }

    @Nullable
    @Override
    public String getUploadPath() {
        return systemMapper.getValueByName(Constants.SystemKey.UPLOAD_PATH);
    }

    @Nullable
    @Cacheable(value = Constants.Cache.SYSTEM, key = "'admin-mail'")
    @Override
    public String getAdminMail() {
        return systemMapper.getValueByName(Constants.SystemKey.ADMIN_MAIL);
    }

    @Nullable
    @Cacheable(value = Constants.Cache.SYSTEM, key = "'ga'")
    @Override
    public String getGa() {
        return systemMapper.getValueByName(Constants.SystemKey.GA);
    }

    @Nullable
    @Cacheable(value = Constants.Cache.SYSTEM, key = "'data-path'")
    @Override
    public String getDataFilesPath() {
        return systemMapper.getValueByName(Constants.SystemKey.DATA_FILES_PATH);
    }

    @Nullable
    @Override
    public String getDeleteTempFile() {
        return systemMapper.getValueByName(Constants.SystemKey.DELETE_TEMP_FILE);
    }

    @Nullable
    @Override
    public String getWorkingPath() {
        return systemMapper.getValueByName(Constants.SystemKey.WORKING_PATH);
    }

    @Nullable
    @Override
    public String getResetPasswordTitle() {
        return systemMapper.getValueByName(Constants.SystemKey.RESETPASSWORD_TITLE);
    }

    @Nullable
    @Cacheable(value = Constants.Cache.SYSTEM, key = "'page-index'")
    @Override
    public String getIndex() {
        return systemMapper.getValueByName(Constants.SystemKey.PAGE_INDEX);
    }

    @CacheEvict(value = Constants.Cache.SYSTEM, key = "'page-index'")
    @Override
    public void setIndex(@Nullable String index) {
        systemMapper.updateValueByName(Constants.SystemKey.PAGE_INDEX, index);
    }

}
