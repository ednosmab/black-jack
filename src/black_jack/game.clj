(ns black-jack.game
  (:require [card-ascii-art.core :as card]))

;; Para testar
;; dentro da pasta D:\black jack Clojure\black-jack\src\black_jack> via Powershel
;; rodar o comando: lein run -m black-jack.game
;; flag -m indica o namespace, black-jack.game é o namespace que contém a aplicação

;; Cartas: A, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K
(defn new-card []
  ;"Generate the cards from A to K for Black-Jack"
  (inc (rand-int 13)))

;; vai alterar o valor das cartas J, Q, K para 10 em cada uma
(defn new-value
  [card]
  (if (> card 10) 
    10
    card))

;; vai alterar o valor da carta A
(defn ace-per-11 
  [card]
  (if (= card 1) 11 card))

;; calculará pontos de acordos com as cartas
;; J, Q, K = 10
;; A = 11, se os pontos das cartas forem maiores que 21 ele valerá 1
(defn points-cards
  [cards]
  (let [cards-without-JQK (map new-value cards)
        cards-with-A11 (map ace-per-11 cards-without-JQK)
        points-with-A-1 (reduce + cards-without-JQK)
        points-with-A-11 (reduce + cards-with-A11)]
  (if (> points-with-A-11 21)
    points-with-A-1
    points-with-A-11)))


;; Jogador
(defn player
  [player-name]
  (let [card-1 (new-card)
        card-2 (new-card)
        cards  [card-1 card-2]
        points (points-cards cards)]
    {:player-name player-name
     :cards       cards
     :points      points}))

;; vai inserir mais cartas 
(defn more-card 
  [player]
  (let [card (new-card)
        cards (conj (:cards player) card)
        new-player (update player :cards conj card)
        points(points-cards cards)]
        (assoc new-player :points points)))


;; dealer automatico pede mais cartas se seus pontos forem menores que o player 1
(defn player-decision-continue?
  [player]
  (println (:player-name player) ": mais cartas? [Sim/Nao]")
  (= (read-line) "sim"))
(defn dealer-decision-continue? [player-points dealer]
  (let [dealer-points (:points dealer)]
    (if (> player-points 21) false 
    (< dealer-points player-points))))

;; vai verificar o ganhador
(defn end-game [player dealer]
  (let [player-points (:points player)
        dealer-points (:points dealer)
        player-name (:player-name player)
        dealer-name (:player-name dealer)
        message (cond
                  (and (> player-points 21) (> dealer-points 21)) "Ambos perderam"
                  (= player-points dealer-points) "empatou"
                  (> player-points 21) (str dealer-name " ganhou")
                  (> dealer-points 21) (str player-name " ganhou")
                  (> player-points dealer-points) (str player-name " ganhou")
                  (> dealer-points player-points) (str dealer-name " ganhou"))]
    (card/print-player player)
    (card/print-player dealer)
    (print message)))

;; vai pedir mais cartas se o jogador solicitar
(defn game 
  [player fn-decision-continue?] 
  (if (fn-decision-continue? player)
    (let [player-with-more-cards (more-card player)]
      (card/print-player player-with-more-cards)
      (recur player-with-more-cards fn-decision-continue?))
    player))

(defn solicit-name []
  (println " Infome o seu nome para iniciar o jogo:")
  (read-line))
(def player-1 (player (solicit-name)))
(card/print-player player-1)

(def dealer (player "Dealer"))
(card/print-masked-player dealer)

(def player-after-game (game player-1 player-decision-continue?))
(def partial-dealer-decision-continue? (partial dealer-decision-continue? (:points player-after-game)))
(def dealer-after-game (game dealer partial-dealer-decision-continue?))

(end-game player-after-game dealer-after-game)
