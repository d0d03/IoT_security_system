import React from 'react';
import { Link } from 'react-router-dom';
import { Result, Button } from 'antd';

const NotFoundPage = () => {

    return(
        <div>
            <Result
                status="404" 
                title="404" 
                subtitle="Sorry, the page you visited does not exist."
                extra={<Link to={"/"}><Button type="primary">Back Home</Button></Link>}
            />
        </div>
    );
}

export { NotFoundPage as default }