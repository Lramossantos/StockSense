$(document).ready(function() {
    // Máscara monetária modificada para formato brasileiro
    $('.money-input').mask('#.##0,00', { 
        reverse: true,
        translation: {
            '0': { pattern: /\d/ },
            ',': { pattern: /[,]/, optional: false },
            '.': { pattern: /[.]/, optional: true }
        },
        onKeyPress: function(value, e, field, options) {
            // Garante que sempre tenha 2 dígitos após a vírgula
            if(value.indexOf(',') >= 0) {
                var parts = value.split(',');
                if(parts[1].length > 2) {
                    field.val(parts[0] + ',' + parts[1].substring(0, 2));
                }
            }
        }
    });

    // Elementos do DOM
    const $fileInput = $('#imagemProduto');
    const $fileLabel = $fileInput.next('.custom-file-label');
    const $feedbackElement = $('#imagemFeedback');
    const $precoCustoInput = $('#precoCusto');
    const validExtensions = ['image/jpeg', 'image/png', 'image/gif', 'image/x-icon', 'image/vnd.microsoft.icon'];

    // Formata o valor do preço de custo ao carregar a página
    if($precoCustoInput.val()) {
        let valor = parseFloat($precoCustoInput.val()).toLocaleString('pt-BR', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
        $precoCustoInput.val(valor);
    }

    // Atualiza o label com o nome do arquivo
    $fileInput.on('change', function(event) {
        const file = event.target.files[0];
        
        if (file) {
            // Mostra apenas o nome do arquivo (sem caminho)
            $fileLabel.addClass("selected").html(file.name);

            // Validação do tipo de arquivo
            if (!validExtensions.includes(file.type) && !file.name.toLowerCase().endsWith('.ico')) {
                showFeedback(false, 'Tipo de arquivo inválido. Use: JPG, PNG, GIF ou ICO');
                resetFileInput();
                return;
            }

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
        $feedbackElement
            .removeClass(isValid ? 'invalid-feedback' : 'valid-feedback')
            .addClass(isValid ? 'valid-feedback' : 'invalid-feedback')
            .text(message)
            .removeClass('d-none');
    }

    // Reseta o input de arquivo
    function resetFileInput() {
        $fileInput.val('');
        $fileLabel.removeClass("selected").html('Selecione uma imagem...');
        $feedbackElement.addClass('d-none').text('');
    }

    // Validação do formulário antes do envio
    $('form').on('submit', function(e) {
        // Validação da imagem
        if ($fileInput[0].files.length === 0) {
            showFeedback(false, '✖ Selecione uma imagem');
            e.preventDefault();
            return false;
        }
        
        // Formata o valor do preço de custo para o padrão americano antes de enviar
        if($precoCustoInput.val()) {
            let valorFormatado = $precoCustoInput.val()
                .replace(/\./g, '')
                .replace(',', '.');
            $precoCustoInput.val(valorFormatado);
        }
        
        return true;
    });

    // Confirmação para exclusão
    $('.btn-excluir').click(function(e) {
        if (!confirm('Tem certeza que deseja excluir este produto?')) {
            e.preventDefault();
        }
    });
});