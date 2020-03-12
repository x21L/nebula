/*******************************************************************************
 *  Copyright (c) 2010 Weltevree Beheer BV, Remain Software & Industrial-TSI
 *
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Wim S. Jongman - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.oscilloscope.snippets;

import org.eclipse.nebula.widgets.oscilloscope.multichannel.Oscilloscope;
import org.eclipse.nebula.widgets.oscilloscope.multichannel.OscilloscopeStackAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * We add a stack listener to provide data when the scope runs out.
 *
 */
public class MultiScope_SimpleScope2 {

	protected static Shell shell;

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.setSize(300, 300);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected static void createContents() {
		shell = new Shell();
		shell.setText("Nebula Oscilloscope");
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		addScope(true, 10);
	}

	private static void addScope(boolean antiAlias, int adder) {
		Oscilloscope scope = new Oscilloscope(shell, SWT.NONE);
		scope.setAntialias(0, antiAlias);

		scope.addStackListener(0, new OscilloscopeStackAdapter() {
			int value = 0;
			int add = adder;

			@Override
			public void stackEmpty(Oscilloscope scope, int channel) {
				scope.setValue(channel, value);
				value += add;
				if (value > 60 || value < -60) {
					add *= -1;
				}
			}
		});

		scope.getDispatcher(0).dispatch();
	}
}
