const getData = async (params) => {
    console.log(params);
    const response = await fetch('/api/dataMax', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Request-Headers': '*',
            'Access-Control-Request-Method': '*'
        },
        body: JSON.stringify(params)
    });
    const body = await response.json();
    if (response.status !== 200) throw Error(body.message);

    //let teste = { data: [{ name: 'r', data: [['2014', 100], ['2015', 71.5], ['2016', 106.4]] }] };
    return await body;
};

export default getData;
