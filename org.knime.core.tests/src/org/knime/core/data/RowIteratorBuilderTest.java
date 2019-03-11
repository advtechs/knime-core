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
 *   6 Mar 2019 (Marc): created
 */
package org.knime.core.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.knime.core.data.predicate.Column.boolCol;
import static org.knime.core.data.predicate.Column.intCol;
import static org.knime.core.data.predicate.Column.longCol;
import static org.knime.core.data.predicate.FilterPredicate.eq;
import static org.knime.core.data.predicate.FilterPredicate.geq;
import static org.knime.core.data.predicate.FilterPredicate.leq;
import static org.knime.core.data.predicate.FilterPredicate.udf;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import org.junit.Test;
import org.knime.core.data.def.BooleanCell;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DefaultTable;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.IntCell;
import org.knime.core.data.def.LongCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.data.predicate.FilterPredicate;

/**
 *
 * @author Marc
 */
public class RowIteratorBuilderTest {

    private static final DataTable TABLE;

    static {
        final DataTableSpec spec = new DataTableSpec(new DataColumnSpecCreator("int", IntCell.TYPE).createSpec(),
            new DataColumnSpecCreator("string", StringCell.TYPE).createSpec(),
            new DataColumnSpecCreator("long", LongCell.TYPE).createSpec(),
            new DataColumnSpecCreator("double", DoubleCell.TYPE).createSpec(),
            new DataColumnSpecCreator("boolean", BooleanCell.TYPE).createSpec());

        final DataRow[] rows = IntStream.range(0, 100)
            .mapToObj(i -> new DefaultRow(new RowKey(Integer.toString(i)), new IntCell(i),
                new StringCell(Integer.toString(i)), new LongCell(i), new DoubleCell(i),
                i % 2 == 1 ? BooleanCell.TRUE : BooleanCell.FALSE))
            .toArray(DataRow[]::new);

        TABLE = new DefaultTable(rows, spec);
    }

    @Test
    public void testRowIteratorBuilderFilterSome() {
        // keep only rows with an index that is even and between 10 and 20 (i.e. 10, 12, 14, 16, 18, 20)
        FilterPredicate pred = geq(intCol(0), 10).and(leq(longCol(2), 20l)).and(eq(boolCol(4), false));

        final RowIterator rowIt = TABLE.iteratorBuilder().filterRowsFrom(13).filterRowsTo(17).filter(pred).build();
        assertTrue(rowIt.hasNext());
        assertEquals("14", rowIt.next().getKey().getString());
        assertTrue(rowIt.hasNext());
        assertEquals("16", rowIt.next().getKey().getString());
        assertFalse(rowIt.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRowIteratorBuilderFilterAll() {
        final RowIterator rowIt = TABLE.iteratorBuilder().filter(udf(intCol(0), i -> false)).build();
        assertFalse(rowIt.hasNext());
        rowIt.next();
    }

    @Test
    public void testRowIteratorBuilderFilterNone() {
        final RowIterator rowIt = TABLE.iteratorBuilder().filter(udf(intCol(0), i -> true)).build();
        final RowIterator rowIt2 = TABLE.iteratorBuilder().build();
        while (rowIt.hasNext() && rowIt2.hasNext()) {
            assertEquals(rowIt.next(), rowIt2.next());
        }
        assertEquals(rowIt.hasNext(), rowIt2.hasNext());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexOutOfBoundsFilterRowsFrom() {
        final RowIterator rowIt = TABLE.iteratorBuilder().filterRowsFrom(-13).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexOutOfBoundsFilterRowsTo() {
        final RowIterator rowIt = TABLE.iteratorBuilder().filterRowsTo(-5).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRangeOfRows() {
        final RowIterator rowIt = TABLE.iteratorBuilder().filterRowsFrom(5).filterRowsTo(3).build();
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsPredicate() {
        final RowIterator rowIt = TABLE.iteratorBuilder().filter(eq(intCol(-1), 0)).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncompatiblePredicate() {
        final RowIterator rowIt = TABLE.iteratorBuilder().filter(eq(boolCol(0), false)).build();
    }

}