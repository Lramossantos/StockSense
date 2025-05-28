// Atualiza o label com o nome do arquivo selecionado
$('#imagemProduto').on('change', function () {
  var fileName = $(this).val().split('\\').pop();
  $(this).next('.custom-file-label').addClass("selected").html(fileName);
});

// Valida o tamanho mínimo da imagem (500x500px)
document.getElementById('imagemProduto').addEventListener('change', function (event) {
  const file = event.target.files[0];
  if (file) {
    const img = new Image();
    img.src = URL.createObjectURL(file);
    img.onload = function () {
      if (this.width < 500 || this.height < 500) {
        alert('A imagem deve ter no mínimo 500x500 pixels.');
        event.target.value = ''; // Limpa o input
        $('.custom-file-label').removeClass("selected").html('Escolher imagem...'); // Reseta o label também
      }
    };
  }
});