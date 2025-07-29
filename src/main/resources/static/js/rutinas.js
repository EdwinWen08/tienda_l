function addCart(formulario) {
    var idProducto=formulario.elements[0].value;
    var existencias=formulario.elements[1].value;
    if (existencias>0) {
        var ruta="/carrito/agregar/"+idProducto;
        //window.alert("La ruta va ser ["+ruta+"]"); Para ver rutas de productos
        $("#resultBlock").load(ruta);
    } else {
        window.alert("No hay existencias.....");
    }
}




function mostrarImagen(input) {

    if (input.files && input.files[0]) {

        var lector = new FileReader();
        lector.onload = function (e) {
            $('#blah').attr('src', e.target.result)
                    .height(200);
        };
        lector.readAsDataURL(input.files[0]);
    }
}

//Para insertar información en el modal según el registro...
document.addEventListener('DOMContentLoaded', function () {
    const confirmModal = document.getElementById('confirmModal');
    confirmModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        document.getElementById('modalId').value = button.getAttribute('data-bs-id');
        document.getElementById('modalDescripcion').textContent = button.getAttribute('data-bs-descripcion');
    });
});


//Para eliminar las ventanas Toast
    setTimeout(() => {
        document.querySelectorAll('.toast').forEach(t => t.classList.remove('show'));
    }, 3000);