/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.collector.ui.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skywalking.apm.collector.client.h2.H2Client;
import org.skywalking.apm.collector.client.h2.H2ClientException;
import org.skywalking.apm.collector.core.util.Const;
import org.skywalking.apm.collector.storage.define.register.ApplicationTable;
import org.skywalking.apm.collector.storage.h2.SqlBuilder;
import org.skywalking.apm.collector.storage.h2.dao.H2DAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pengys5, clevertension
 */
public class ApplicationH2DAO extends H2DAO implements IApplicationDAO {

    private final Logger logger = LoggerFactory.getLogger(ApplicationH2DAO.class);
    private static final String GET_APPLICATION_CODE_SQL = "select {0} from {1} where {2} = ?";
    @Override public String getApplicationCode(int applicationId) {
        logger.debug("get application code, applicationId: {}", applicationId);
        H2Client client = getClient();
        String sql = SqlBuilder.buildSql(GET_APPLICATION_CODE_SQL, ApplicationTable.COLUMN_APPLICATION_CODE, ApplicationTable.TABLE, ApplicationTable.COLUMN_APPLICATION_ID);
        Object[] params = new Object[]{applicationId};
        try (ResultSet rs = client.executeQuery(sql, params)) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException | H2ClientException e) {
            logger.error(e.getMessage(), e);
        }
        return Const.UNKNOWN;
    }
}
