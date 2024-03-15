export const insertEmployee = (data) => {
    fetch('/employees/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                return response.json();

            } else {
                return response.json().then(errors => {
                    console.error('Validation errors:', errors);
                });
            }
        })
        .then(data => {
            console.log('Employee inserted successfully:', data);

        })
        .catch(error => {
            console.error('Failed to insert employee:', error);

        });
}
