CREATE TABLE Commande (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          description TEXT NOT NULL,
                          quantite INT NOT NULL,
                          date DATE NOT NULL,
                          montant DOUBLE NOT NULL
);

