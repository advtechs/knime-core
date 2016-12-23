/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ---------------------------------------------------------------------
 *
 */
package org.knime.core.thrift.workflow.entity;

import org.knime.core.gateway.v0.workflow.entity.XYEnt;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.facebook.swift.codec.ThriftConstructor;
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

import org.knime.core.gateway.v0.workflow.entity.TestEnt;
import org.knime.core.gateway.v0.workflow.entity.builder.TestEntBuilder;

import org.knime.core.thrift.workflow.entity.TTestEnt.TTestEntBuilder;
import org.knime.core.thrift.workflow.entity.TTestEntFromThrift.TTestEntBuilderFromThrift;
import org.knime.core.thrift.TEntityBuilderFactory.ThriftEntityBuilder;
import org.knime.core.gateway.v0.workflow.entity.builder.GatewayEntityBuilder;


/**
 *
 * @author Martin Horn, University of Konstanz
 */
@ThriftStruct(builder = TTestEntBuilder.class)
public class TTestEnt {



	private TXYEnt m_xy;
	private List<TXYEnt> m_xylist;
	private String m_other;
	private List<String> m_primitivelist;
	private Map<String, TXYEnt> m_xymap;
	private Map<Integer, String> m_primitivemap;

    /**
     * @param builder
     */
    private TTestEnt(final TTestEntBuilder builder) {
		m_xy = builder.m_xy;
		m_xylist = builder.m_xylist;
		m_other = builder.m_other;
		m_primitivelist = builder.m_primitivelist;
		m_xymap = builder.m_xymap;
		m_primitivemap = builder.m_primitivemap;
    }
    
    protected TTestEnt() {
    	//
    }

    @ThriftField(1)
    public TXYEnt getxy() {
        return m_xy;
    }
    
    @ThriftField(2)
    public List<TXYEnt> getxylist() {
        return m_xylist;
    }
    
    @ThriftField(3)
    public String getother() {
        return m_other;
    }
    
    @ThriftField(4)
    public List<String> getprimitivelist() {
        return m_primitivelist;
    }
    
    @ThriftField(5)
    public Map<String, TXYEnt> getxymap() {
        return m_xymap;
    }
    
    @ThriftField(6)
    public Map<Integer, String> getprimitivemap() {
        return m_primitivemap;
    }
    

	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}

	public static TTestEntBuilder builder() {
		return new TTestEntBuilder();
	}
	
    public static class TTestEntBuilder implements ThriftEntityBuilder<TestEnt> {
    
		private TXYEnt m_xy;
		private List<TXYEnt> m_xylist;
		private String m_other;
		private List<String> m_primitivelist;
		private Map<String, TXYEnt> m_xymap;
		private Map<Integer, String> m_primitivemap;

        @ThriftConstructor
        public TTestEnt build() {
            return new TTestEnt(this);
        }
        
        @Override
        public GatewayEntityBuilder<TestEnt> wrap() {
            return new TTestEntBuilderFromThrift(this);
        }

        @ThriftField
        public TTestEntBuilder setxy(final TXYEnt xy) {
			m_xy = xy;			
            return this;
        }
        
        @ThriftField
        public TTestEntBuilder setxylist(final List<TXYEnt> xylist) {
			m_xylist = xylist;			
            return this;
        }
        
        @ThriftField
        public TTestEntBuilder setother(final String other) {
			m_other = other;			
            return this;
        }
        
        @ThriftField
        public TTestEntBuilder setprimitivelist(final List<String> primitivelist) {
			m_primitivelist = primitivelist;			
            return this;
        }
        
        @ThriftField
        public TTestEntBuilder setxymap(final Map<String, TXYEnt> xymap) {
			m_xymap = xymap;			
            return this;
        }
        
        @ThriftField
        public TTestEntBuilder setprimitivemap(final Map<Integer, String> primitivemap) {
			m_primitivemap = primitivemap;			
            return this;
        }
        
    }

}
