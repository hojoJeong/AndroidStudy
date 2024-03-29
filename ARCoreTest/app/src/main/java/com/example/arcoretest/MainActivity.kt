package com.example.arcoretest

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment
import com.google.ar.sceneform.ux.TransformableNode

class MainActivity : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arFragment = supportFragmentManager.findFragmentById(R.id.fg_arcore) as ArFragment
        arFragment.setOnTapArPlaneListener(BaseArFragment.OnTapArPlaneListener{hitResult, plane, motionEvent ->
            var anchor : Anchor = hitResult.createAnchor()

            ModelRenderable.builder()
                .setSource(this, Uri.parse("sofa.gltf"))
                .build()
                .thenAccept { addModelToScence(anchor, it) }
                .exceptionally {
                    val builder : AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setMessage(it.localizedMessage)
                        .show()
                    return@exceptionally null
                }
        })
    }

    private fun addModelToScence(anchor: Anchor, it: ModelRenderable?) {
        val anchorNode : AnchorNode = AnchorNode(anchor)
        val transform : TransformableNode = TransformableNode(arFragment.transformationSystem)
        transform.setParent(anchorNode)
        transform.renderable = it
        arFragment.arSceneView.scene.addChild(anchorNode)
        transform.select()

    }
}