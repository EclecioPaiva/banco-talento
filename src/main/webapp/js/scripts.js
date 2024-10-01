// scripts.js

// Função para obter os parâmetros da URL
function obterParametrosURL() {
    const params = new URLSearchParams(window.location.search);
    return {
        id: params.get('id')
    };
}

// Função para buscar os dados e preencher a tabela
async function carregarTalentos() {
    const url = '/banco-talento/talentos/';
    const tabelaCorpo = document.querySelector('#talentos-table tbody');
    const mensagem = document.getElementById('message');

    try {
        const resposta = await fetch(url);

        // Verifica se a resposta foi bem-sucedida
        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} ${resposta.statusText}`);
        }

        const dados = await resposta.json();

        // Limpa o conteúdo anterior da tabela, se houver
        tabelaCorpo.innerHTML = '';

        // Verifica se há dados
        if (dados.length === 0) {
            mensagem.textContent = 'Nenhum talento encontrado.';
            return;
        }

        // Itera sobre os dados e cria as linhas da tabela
        dados.forEach(talento => {
            const linha = document.createElement('tr');

            const colunaId = document.createElement('td');
            colunaId.textContent = talento.id;
            linha.appendChild(colunaId);

            const colunaNome = document.createElement('td');
            colunaNome.textContent = talento.nome;
            linha.appendChild(colunaNome);

            const colunaAcoes = document.createElement('td');

            // Link para Editar
            const linkEditar = document.createElement('a');
            linkEditar.textContent = 'Editar';
            linkEditar.href = `editar.html?id=${talento.id}`;
            linkEditar.classList.add('action-link');
            colunaAcoes.appendChild(linkEditar);

            // Link para Excluir
            const linkExcluir = document.createElement('a');
            linkExcluir.textContent = 'Excluir';
            linkExcluir.href = '#';
            linkExcluir.classList.add('action-link');
            linkExcluir.addEventListener('click', (event) => {
                event.preventDefault();
                excluirTalento(talento.id);
            });
            colunaAcoes.appendChild(linkExcluir);

            linha.appendChild(colunaAcoes);

            tabelaCorpo.appendChild(linha);
        });

    } catch (erro) {
        console.error('Erro ao carregar os talentos:', erro);
        mensagem.textContent = 'Não foi possível carregar os talentos. Por favor, tente novamente mais tarde.';
    }
}

// Função para excluir um talento
async function excluirTalento(id) {
    const confirmacao = confirm(`Tem certeza que deseja excluir o talento com ID ${id}?`);
    if (!confirmacao) return;

    const url = `/banco-talento/talentos/${id}`;
    const mensagem = document.getElementById('message');

    try {
        const resposta = await fetch(url, {
            method: 'DELETE'
        });

        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} ${resposta.statusText}`);
        }

        // Recarrega a tabela após a exclusão
        carregarTalentos();
        mensagem.textContent = 'Talento excluído com sucesso.';
        mensagem.className = 'success';
        setTimeout(() => {
            mensagem.textContent = '';
            mensagem.className = '';
        }, 3000);

    } catch (erro) {
        console.error('Erro ao excluir o talento:', erro);
        mensagem.textContent = 'Não foi possível excluir o talento. Por favor, tente novamente mais tarde.';
        mensagem.className = 'error';
    }
}

// Função para carregar os detalhes de um talento para edição
async function carregarTalentoParaEdicao() {
    const parametros = obterParametrosURL();
    const id = parametros.id;
    const mensagem = document.getElementById('message');

    if (!id) {
        mensagem.textContent = 'ID do talento não fornecido.';
        mensagem.className = 'error';
        return;
    }

    const url = `/banco-talento/talentos/${id}`;

    try {
        const resposta = await fetch(url);

        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} ${resposta.statusText}`);
        }

        const talento = await resposta.json();

        // Preenche os campos do formulário
        document.getElementById('id').value = talento.id;
        document.getElementById('nome').value = talento.nome;

    } catch (erro) {
        console.error('Erro ao carregar o talento:', erro);
        mensagem.textContent = 'Não foi possível carregar os detalhes do talento.';
        mensagem.className = 'error';
    }
}

// Função para lidar com o envio do formulário de edição
async function salvarAlteracoes(event) {
    event.preventDefault();

    const id = document.getElementById('id').value;
    const nome = document.getElementById('nome').value;
    const mensagem = document.getElementById('message');

    const url = `/banco-talento/talentos/${id}`;

    const dados = {
        nome: nome
    };

    try {
        const resposta = await fetch(url, {
            method: 'PUT', // ou 'POST', dependendo da sua API
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dados)
        });

        if (!resposta.ok) {
            throw new Error(`Erro na requisição: ${resposta.status} ${resposta.statusText}`);
        }

        mensagem.textContent = 'Talento atualizado com sucesso.';
        mensagem.className = 'success';
        setTimeout(() => {
            window.location.href = 'index.html';
        }, 2000);

    } catch (erro) {
        console.error('Erro ao atualizar o talento:', erro);
        mensagem.textContent = 'Não foi possível atualizar o talento.';
        mensagem.className = 'error';
    }
}

// Função para inicializar a página principal
function inicializarPaginaPrincipal() {
    carregarTalentos();
}

// Função para inicializar a página de edição
function inicializarPaginaEdicao() {
    carregarTalentoParaEdicao();

    const formulario = document.getElementById('editar-form');
    formulario.addEventListener('submit', salvarAlteracoes);
}

// Inicializa a página correta com base no conteúdo do DOM
document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('talentos-table')) {
        inicializarPaginaPrincipal();
    }

    if (document.getElementById('editar-form')) {
        inicializarPaginaEdicao();
    }
});
