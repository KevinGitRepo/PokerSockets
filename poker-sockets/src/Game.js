import React, {useState} from "react";
import "./Game.css";

export function Game(props) {

    const [playerBetAmount, setPlayerBetAmount] = useState('');

    const handleCheck = () => {
        props.setPlayersTurn(false);
        handleSocketSending("check", "");
    }

    const handleFold = () => {
        props.setPlayersTurn(false);
        handleSocketSending("fold", "");
    }

    const handleBet = () => {
        if( parseInt(playerBetAmount) <= props.moneyLimit ) {
            props.setMoneyLimit(() => props.moneyLimit - parseInt(playerBetAmount));
            props.setPlayersTurn(false);
            props.setPot(playerBetAmount);
            handleSocketSending("bet", playerBetAmount);
            setPlayerBetAmount('');
        }
        else{
            props.setMessages("Amount too high! Lower betting amount.");
        }
    }

    const handleOnChangeBet = (e) => {
        const value = e.target.value;

        if (value === '' || !isNaN(value)){
            setPlayerBetAmount(value);
        }
    };

    const handleSocketSending = (action, message) => {
        props.socket.send(JSON.stringify({action: action, data: message}));
    }

    return (
        <div>
            {props.isPlayerCreated &&
                <div className={"game"}>
                    <h1 className={"game-title"}>Welcome to Poker</h1>
                    <h3 className={"game-dealer"}>Dealer's Cards: {props.dealersCards}</h3>
                    <h4 className={"game-hand"}>Your Cards: {props.playersCards}</h4>
                    <h5 className={"game-limit"}>Bet Amount: {props.moneyLimit}</h5>
                    {props.isPlayerTurn &&
                        <div className={"game-actions"}>
                            <div className={"game-bet-row"}>
                                <button className={"game-button game-button-bet"} onClick={handleBet}>Bet</button>
                                <input className={"game-input"} type="number" value={playerBetAmount} onChange={handleOnChangeBet} placeholder="amount"/>
                            </div>
                            <div>
                                <button className={"game-button game-button-check"} onClick={handleCheck}>Check</button>
                            </div>
                            <div>
                                <button className={"game-button game-button-fold"} onClick={handleFold}>Fold</button>
                            </div>

                        </div>
                    }
                </div>
            }
        </div>
    )
}