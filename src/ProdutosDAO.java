
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    
    
    public void cadastrarProduto (ProdutosDTO produto){
        try {
            conn = new conectaDAO().connectDB();

            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            
            prep = conn.prepareStatement(sql);

            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();

        try {
            // Estabelece conexão com o banco de dados
            conn = new conectaDAO().connectDB();

            // Consulta SQL para listar todos os produtos
            String sql = "SELECT id, nome, valor, status FROM produtos";

            // Prepara a execução da consulta
            prep = conn.prepareStatement(sql);

            // Executa a consulta e armazena os resultados
            resultset = prep.executeQuery();

            // Itera pelos resultados e adiciona ao ArrayList
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));            // Lê o campo "id"
                produto.setNome(resultset.getString("nome"));     // Lê o campo "nome"
                produto.setValor(resultset.getInt("valor"));   // Lê o campo "valor"
                produto.setStatus(resultset.getString("status")); // Lê o campo "status"

                listagem.add(produto); // Adiciona o objeto à lista
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }
        return listagem;
    }
    
    public void venderProduto(int idProduto) {
        try {
            conn = new conectaDAO().connectDB();

            // Consulta SQL para atualizar o status do produto
            String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

            prep = conn.prepareStatement(sql);
            prep.setInt(1, idProduto);
            int linhasAfetadas = prep.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto marcado como 'Vendido' com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID informado.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        }
    }
    
        
}

