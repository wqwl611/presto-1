/*
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
 */
package io.prestosql.server;

import com.google.common.collect.Maps;
import io.prestosql.failureDetector.HeartbeatFailureDetector;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import java.util.Collection;

import static com.google.common.base.Predicates.in;

@Path("/v1/node")
public class NodeResource
{
    private final HeartbeatFailureDetector failureDetector;

    @Inject
    public NodeResource(HeartbeatFailureDetector failureDetector)
    {
        this.failureDetector = failureDetector;
    }

    @GET
    public Collection<HeartbeatFailureDetector.Stats> getNodeStats()
    {
        return failureDetector.getStats().values();
    }

    @GET
    @Path("failed")
    public Collection<HeartbeatFailureDetector.Stats> getFailed()
    {
        return Maps.filterKeys(failureDetector.getStats(), in(failureDetector.getFailed())).values();
    }
}