/*-
 * #%L
 * A Maven project implementing an ImageJ command.
 * %%
 * Copyright (C) 2017 - 2024 My Company, Inc.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 */
@file:JvmMultifileClass

package com.mycompany.imagej

import net.imagej.Dataset
import net.imagej.ImageJ
import net.imagej.ops.OpService
import net.imglib2.img.Img
import net.imglib2.type.numeric.RealType
import org.scijava.command.Command
import org.scijava.plugin.Parameter
import org.scijava.plugin.Plugin
import org.scijava.ui.UIService

fun main() {
    // create the ImageJ application context with all available services
    val ij = ImageJ()

    val dataset = DatasetOpener.open(ij)

    if (dataset == null)
        ij.ui().showDialog("Dataset is null!")

    // show the image
    ij.ui().show(dataset)

    // invoke the plugin
    ij.command().run(GaussFiltering::class.java, true)
}

/**
 * This example illustrates how to create an ImageJ [Command] plugin.
 *
 *
 * The code here is a simple Gaussian blur using ImageJ Ops.
 *
 *
 *
 * You should replace the parameter fields with your own inputs and outputs,
 * and replace the [run] method implementation with your own logic.
 *
 */
@Plugin(type = Command::class, menuPath = "Plugins > Gauss Filtering")
open class GaussFiltering<T : RealType<T>> : Command {
    //
    // Feel free to add more parameters here...
    //

    @Parameter
    private var currentData: Dataset? = null

    @Parameter
    private var uiService: UIService? = null

    @Parameter
    private var opService: OpService? = null

    override fun run() {
        currentData?.let { currentData ->
            val image = currentData.imgPlus as Img<T>

            //
            // Enter image processing code here ...
            // The following is just a Gauss filtering example
            //
            val sigmas = doubleArrayOf(1.0, 3.0, 5.0)

            val results = sigmas.map { opService!!.filter().gauss<T, T>(image, it) }

            // display result
            for (elem in results) {
                uiService!!.show(elem)
            }
        }
    }
}
