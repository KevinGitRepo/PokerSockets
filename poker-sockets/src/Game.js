import React, {useState} from "react";

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
                <div>
                    <h1>Welcome to Poker</h1>
                    <h3>Dealer's Cards: {props.dealersCards}</h3>
                    <h4>Your Cards: {props.playersCards}</h4>
                    <h5>Bet Amount: {props.moneyLimit}</h5>
                    {props.isPlayerTurn &&
                        <div>
                            <div>
                                <button onClick={handleBet}>Bet</button>
                                <input type="number" value={playerBetAmount} onChange={handleOnChangeBet} placeholder="amount"/>
                            </div>
                            <div>
                                <button onClick={handleCheck}>Check</button>
                            </div>
                            <div>
                                <button onClick={handleFold}>Fold</button>
                            </div>

                        </div>
                    }
                </div>
            }
        </div>
    )
}