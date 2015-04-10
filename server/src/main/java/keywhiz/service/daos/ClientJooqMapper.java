/*
 * Copyright (C) 2015 Square, Inc.
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

package keywhiz.service.daos;

import keywhiz.api.model.Client;
import keywhiz.jooq.tables.records.ClientsRecord;
import org.jooq.Record;
import org.jooq.RecordMapper;

/**
 * Jooq has the ability to map records to classes using Reflection. We however need a mapper because
 * the constructor's parameter and the columns in the database do not share the same order.
 *
 * In general, I feel having a mapper is cleaner, so it might not be a bad thing.
 *
 * The way jooq built their generic API is somewhat broken, so we need to implement
 * RecordMapper<Record, Client> instead of RecordMapper<ClientsRecord, Client>. I'll file a task
 * and follow up on this issue.
 */
class ClientJooqMapper implements RecordMapper<Record, Client> {
  public Client map(Record record) {
    // :(
    ClientsRecord r = (ClientsRecord) record;
    return new Client(r.getId(), r.getName(), r.getDescription(), r.getCreatedat(),
        r.getCreatedby(), r.getUpdatedat(), r.getUpdatedby(), r.getEnabled(),
        r.getAutomationallowed());
  }
}
