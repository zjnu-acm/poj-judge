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
package cn.edu.zjnu.acm.judge.data.form;

import cn.edu.zjnu.acm.judge.data.excel.Account;
import cn.edu.zjnu.acm.judge.mapper.UserMapper;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 *
 * @author zhanhb
 */
@Data
@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField", "PublicInnerClass"})
public class AccountImportForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Account> content;
    private final Set<ExistPolicy> existsPolicy = EnumSet.noneOf(ExistPolicy.class);

    /**
     * {@link UserMapper#batchUpdate(java.util.List, int)}
     */
    public enum ExistPolicy {
        ENABLE,
        RESET_PASSWORD,
        RESET_USERINFO,
    }

}
