const getData = async (params) => {
    const response = await fetch('/api/updateChart', {body: params});
    const body = await response.json();
    if (response.status !== 200) throw Error(body.message);
    console.log(body);
    return body;
}

export default getData
