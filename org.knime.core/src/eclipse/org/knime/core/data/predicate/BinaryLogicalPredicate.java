/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by KNIME AG, Zurich, Switzerland
 *  Website: http://www.knime.com; Email: contact@knime.com
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
 *  KNIME and ECLIPSE being a combined program, KNIME AG herewith grants
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
 * History
 *   11 Mar 2019 (Marc): created
 */
package org.knime.core.data.predicate;

import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;

/**
 *
 * @author Marc
 * @since 3.8
 */
@SuppressWarnings("javadoc")
public abstract class BinaryLogicalPredicate implements FilterPredicate {
    private final FilterPredicate m_left;

    private final FilterPredicate m_right;

    private BinaryLogicalPredicate(final FilterPredicate left, final FilterPredicate right) {
        m_left = left;
        m_right = right;
    }

    @Override
    public FilterPredicate validate(final DataTableSpec spec) {
        m_left.validate(spec);
        m_right.validate(spec);
        return this;
    }

    public FilterPredicate getLeft() {
        return m_left;
    }

    public FilterPredicate getRight() {
        return m_right;
    }

    public static final class And extends BinaryLogicalPredicate {
        And(final FilterPredicate left, final FilterPredicate right) {
            super(left, right);
        }

        @Override
        public boolean keep(final DataRow row) {
            return getLeft().keep(row) && getRight().keep(row);
        }
    }

    public static final class Or extends BinaryLogicalPredicate {
        Or(final FilterPredicate left, final FilterPredicate right) {
            super(left, right);
        }

        @Override
        public boolean keep(final DataRow row) {
            return getLeft().keep(row) || getRight().keep(row);
        }
    }

}
