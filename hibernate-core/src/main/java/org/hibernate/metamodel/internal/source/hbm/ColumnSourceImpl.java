/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.metamodel.internal.source.hbm;

import org.hibernate.TruthValue;
import org.hibernate.internal.jaxb.mapping.hbm.JaxbColumnElement;
import org.hibernate.metamodel.spi.relational.JdbcDataType;
import org.hibernate.metamodel.spi.relational.Size;
import org.hibernate.metamodel.spi.source.ColumnSource;

/**
* @author Steve Ebersole
*/
class ColumnSourceImpl implements ColumnSource {
	private final String tableName;
	private final JaxbColumnElement columnElement;
	private final TruthValue includedInInsert;
	private final TruthValue includedInUpdate;
    private final TruthValue nullable;

	ColumnSourceImpl(
			String tableName,
			JaxbColumnElement columnElement,
			TruthValue isIncludedInInsert,
			TruthValue isIncludedInUpdate) {
		this( tableName, columnElement, isIncludedInInsert, isIncludedInUpdate, TruthValue.UNKNOWN );
	}

    ColumnSourceImpl(
            String tableName,
            JaxbColumnElement columnElement,
            TruthValue isIncludedInInsert,
            TruthValue isIncludedInUpdate,
            TruthValue nullable) {
        this.tableName = tableName;
        this.columnElement = columnElement;
        this.nullable = nullable;
        this.includedInInsert = isIncludedInInsert;
        this.includedInUpdate = isIncludedInUpdate;
    }

	@Override
	public Nature getNature() {
		return Nature.COLUMN;
	}

	@Override
	public String getName() {
		return columnElement.getName();
	}

	@Override
	public TruthValue isNullable() {
		return nullable;
	}

	@Override
	public String getDefaultValue() {
		return columnElement.getDefault();
	}

	@Override
	public String getSqlType() {
		return columnElement.getSqlType();
	}

	@Override
	public JdbcDataType getDatatype() {
		return null;
	}

	@Override
	public Size getSize() {
		return new Size(
				Helper.getIntValue( columnElement.getPrecision(), -1 ),
				Helper.getIntValue( columnElement.getScale(), -1 ),
				Helper.getLongValue( columnElement.getLength(), -1 ),
				Size.LobMultiplier.NONE
		);
	}

	@Override
	public String getReadFragment() {
		return columnElement.getRead();
	}

	@Override
	public String getWriteFragment() {
		return columnElement.getWrite();
	}

	@Override
	public boolean isUnique() {
		return columnElement.isUnique();
	}

	@Override
	public String getCheckCondition() {
		return columnElement.getCheck();
	}

	@Override
	public String getComment() {
		return columnElement.getComment();
	}

	@Override
	public TruthValue isIncludedInInsert() {
		return includedInInsert;
	}

	@Override
	public TruthValue isIncludedInUpdate() {
		return includedInUpdate;
	}

	@Override
	public String getContainingTableName() {
		return tableName;
	}
}