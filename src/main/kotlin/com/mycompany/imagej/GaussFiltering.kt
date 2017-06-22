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

    companion object {

        /**
         * This main function serves for development purposes.
         * It allows you to run the plugin immediately out of
         * your integrated development environment (IDE).

         * @param args whatever, it's ignored
         * *
         * @throws Exception
         */
        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            // create the ImageJ application context with all available services
            val ij = ImageJ()
            ij.ui().showUI()
			
            Dummy().hello()


            // ask the user for a file to open
//            val file = ij.ui().chooseFile(null, "open")
//
//            if (file != null) {
//                // load the dataset
//                val dataset = ij.scifio().datasetIO().open(file.path)
//
//                // show the image
//                ij.ui().show(dataset)
//
//                // invoke the plugin
//                ij.command().run<GaussFiltering<*>>(GaussFiltering::class.java, true)
//            }
        }
    }

}
