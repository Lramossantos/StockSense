$(document).ready(function() {
    // Elementos do DOM
    const $fileInput = $('#imagemProduto');
    const $fileLabel = $fileInput.next('.custom-file-label');
    const $feedbackElement = $('#imagemFeedback');
    
    // Atualiza o label com o caminho do arquivo
    $fileInput.on('change', function(event) {
        const file = event.target.files[0];
        
        if (file) {
            // Mostra o caminho completo do arquivo selecionado
            const fullPath = $(this).val();
            // Remove o prefixo 'C:\fakepath\' se existir
            const displayPath = fullPath.replace(/^C:\\fakepath\\/i, '');
            $fileLabel.addClass("selected").html(displayPath);
            
            // Valida a imagem
            validateImage(file, function(result) {
                if (result.valid) {
                    showFeedback(true, `✔ Imagem válida (${result.width}x${result.height}px)`);
                } else {
                    showFeedback(false, `✖ ${result.message}`);
                    resetFileInput();
                }
            });
        } else {
            resetFileInput();
        }
    });
    
    // Função para validar a imagem
    function validateImage(file, callback) {
        // Verifica se é uma imagem
        if (!file.type.match('image.*')) {
            callback({
                valid: false,
                message: 'O arquivo deve ser uma imagem'
            });
            return;
        }
        
        // Verifica tamanho do arquivo (máximo 5MB)
        if (file.size > 5 * 1024 * 1024) {
            callback({
                valid: false,
                message: 'A imagem deve ter menos de 5MB'
            });
            return;
        }
        
        // Verifica dimensões
        const img = new Image();
        img.src = URL.createObjectURL(file);
        
        img.onload = function() {
            const isValid = this.width >= 500 && this.height >= 500;
            callback({
                valid: isValid,
                width: this.width,
                height: this.height,
                message: isValid ? '' : `A imagem deve ter no mínimo 500x500px (${this.width}x${this.height}px)`
            });
            URL.revokeObjectURL(img.src);
        };
        
        img.onerror = function() {
            callback({
                valid: false,
                message: 'Não foi possível carregar a imagem'
            });
        };
    }
    
    // Mostra feedback visual
    function showFeedback(isValid, message) {
        $feedbackElement.removeClass('d-none')
                       .removeClass(isValid ? 'invalid-feedback' : 'valid-feedback')
                       .addClass(isValid ? 'valid-feedback' : 'invalid-feedback')
                       .html(message);
    }
    
    // Reseta o input de arquivo
    function resetFileInput() {
        $fileInput.val('');
        $fileLabel.removeClass("selected").html('Selecione uma imagem...');
        $feedbackElement.addClass('d-none').html('');
    }
    
    // Validação do formulário antes do envio
    $('form').on('submit', function(e) {
        if ($fileInput[0].files.length === 0) {
            showFeedback(false, '✖ Selecione uma imagem');
            e.preventDefault();
            return false;
        }
        return true;
    });
	
	$(document).ready(function() {
	    $('.btn-excluir').click(function(e) {
	        if (!confirm('Tem certeza que deseja excluir este produto?')) {
	            e.preventDefault();
	        }
	    });
	});
	
	document.getElementById('imagemProduto').addEventListener('change', function(e) {
	    const file = e.target.files[0];
	    const label = document.getElementById('imagemLabel');
	    const feedback = document.getElementById('imagemFeedback');
	    
	    if (file) {
	        label.textContent = file.name;
	        
	        // Validação simples do tipo de arquivo
	        const validExtensions = ['image/x-icon', 'image/vnd.microsoft.icon', 'image/*'];
	        const fileType = file.type;
	        
	        if (!validExtensions.includes(fileType) && !file.name.endsWith('.ico')) {
	            feedback.textContent = 'Por favor, selecione um arquivo .ico ou imagem válida';
	            feedback.classList.remove('d-none');
	            feedback.classList.add('text-danger');
	            e.target.value = ''; // Limpa o input
	            label.textContent = 'Selecione um ícone (.ico) ou imagem...';
	        } else {
	            feedback.classList.add('d-none');
	        }
	    }
	});
});