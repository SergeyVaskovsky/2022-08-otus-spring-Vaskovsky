import React, {useEffect, useState} from 'react';
import logo from "../assets/loading.gif";

const Loading = ({isLoading}) => {
    const [loading, setLoading] = useState(isLoading);

    useEffect(() => {
        const onPageLoad = () => {
            setLoading(isLoading);
        };

        if (document.readyState === 'complete') {
            onPageLoad();
        } else {
            window.addEventListener('load', onPageLoad);
            return () => window.removeEventListener('load', onPageLoad);
        }
    }, [isLoading]);

    return (
        <div style={{
            transform: 'translate(-50%, -50%)',
            position: 'absolute',
            zIndex: 999,
            top: '50%',
            left: '50%',
            overflow: 'auto'
        }}>
            {
                loading ? <img src={logo} alt="loading..."/> : ""
            }
        </div>
    );
}

export default Loading;