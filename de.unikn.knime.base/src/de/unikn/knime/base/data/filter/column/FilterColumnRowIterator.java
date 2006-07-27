/*
 * --------------------------------------------------------------------- *
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 *
 * Copyright, 2003 - 2006
 * University of Konstanz, Germany.
 * Chair for Bioinformatics and Information Mining
 * Prof. Dr. Michael R. Berthold
 *
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner or
 * as specified in the license file distributed with this product.
 *
 * If you have any quesions please contact the copyright holder:
 * website: www.knime.org
 * email: contact@knime.org
 * --------------------------------------------------------------------- *
 */
package de.unikn.knime.base.data.filter.column;

import de.unikn.knime.core.data.DataRow;
import de.unikn.knime.core.data.RowIterator;

/**
 * Filter column row iterator needed to wrap the filter column's original row
 * iterator.
 * 
 * @see FilterColumnTable
 * 
 * @author Thomas Gabriel, University of Konstanz
 */
final class FilterColumnRowIterator extends RowIterator {

    /*
     * Original row iterator.
     */
    private final RowIterator m_it;

    /*
     * Array of column indices.
     */
    private final int[] m_columns;

    /**
     * Creates a new filter iterator using the original row iterator and an
     * array of column indices.
     * 
     * @param it the original row iterator
     * @param columns an array of column indices
     */
    FilterColumnRowIterator(final RowIterator it, final int[] columns) {
        m_it = it;
        m_columns = columns;
    }

    /**
     * @see de.unikn.knime.core.data.RowIterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return m_it.hasNext();
    }

    /**
     * @see de.unikn.knime.core.data.RowIterator#next()
     */
    @Override
    public DataRow next() {
        return new FilterColumnRow(m_it.next(), m_columns);
    }
}
