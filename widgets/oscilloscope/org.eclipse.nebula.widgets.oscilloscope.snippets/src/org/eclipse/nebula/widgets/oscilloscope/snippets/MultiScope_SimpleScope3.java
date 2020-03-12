/*******************************************************************************
 *  Copyright (c) 2020 Wim Jongman
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
 * This snippet draws an oscillating standing wave for the whole width of the
 * scope.
 *
 */
public class MultiScope_SimpleScope3 {

	protected static Shell shell;

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Display display = Display.getDefault();
		createContents();
		shell.setSize(500, 300);
		shell.open();
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
		shell.setLayout(new FillLayout());

		// Create a single channel scope
		Oscilloscope scope = new Oscilloscope(shell, SWT.NONE);
		scope.setTailSize(0, Oscilloscope.TAILSIZE_MAX);
		scope.addListener(SWT.Resize, e -> scope.setProgression(0, scope.getSize().x));
		scope.setPercentage(0, true);
		scope.setAntialias(0, true);
		scope.setCursorPosition(0, 0);
		scope.getDispatcher(0).setDelayLoop(0);

		scope.addStackListener(0, new OscilloscopeStackAdapter() {
			double mul = 1;
			int adder = 2;
			@Override
			public void stackEmpty(Oscilloscope scope, int channel) {

				int[] values = new int[scope.getSize().x];
				for (int i = 0; i < values.length; i++) {

					values[i] = ((int) (mul * Math.sin((i / 100.0) * 2 * Math.PI)));
				}
				mul += adder;
				if (mul > 50 || mul < -50) {
					adder *= -1;
				}
				scope.setValues(channel, values);
			}
		});

		scope.getDispatcher(0).dispatch();
	}
}
