import org.openrndr.application
import org.openrndr.draw.*
import org.openrndr.extras.camera.Orbital
import org.openrndr.extras.meshgenerators.boxMesh

fun main() = application {
    program {
        val cubemap1 = Cubemap.fromUrl("file:demo-data/cubemaps/garage_iem.dds", session = Session.active)
        val cube = boxMesh()
        val cubemap2 = cubemap(cubemap1.width, format = cubemap1.format, type = cubemap1.type, levels = 2, session = Session.active)
        cubemap1.copyTo(cubemap2, 0, 0)
        cubemap2.generateMipmaps()

        val cma = arrayCubemap(256, 10)
        extend(Orbital()) {

        }
        extend {
            drawer.shadeStyle = shadeStyle {
                fragmentTransform = """
                    x_fill = texture(p_cma, vec4(va_position,0.0)); 
                """
                parameter("cubemap", cubemap2)
                parameter("cma", cma)
            }
            drawer.vertexBuffer(cube, DrawPrimitive.TRIANGLES)
        }
    }
}