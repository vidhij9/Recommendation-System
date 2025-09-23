const API_BASE = '/api/shipments';

const shipmentsTableBody = document.querySelector('#shipments-table tbody');
const timelineSection = document.querySelector('.timeline-section');
const timelineContainer = document.querySelector('#timeline');
const createForm = document.querySelector('#create-shipment-form');
const addEventForm = document.querySelector('#add-event-form');
const createFeedback = document.querySelector('#create-feedback');
const eventFeedback = document.querySelector('#event-feedback');
const refreshButton = document.querySelector('#refresh');
const eventTrackingNumberInput = document.querySelector('#event-tracking-number');

function formatDate(value) {
    return new Date(value).toLocaleString();
}

function statusLabel(status) {
    return status.replace('_', ' ').toUpperCase();
}

function clearFeedback() {
    showFeedback(createFeedback, '');
    showFeedback(eventFeedback, '');
}

function showFeedback(element, message, type) {
    if (!element) return;
    element.textContent = message;
    element.classList.remove('error', 'success');
    if (message && type) {
        element.classList.add(type);
    }
}

async function handleResponse(response) {
    if (response.ok) {
        if (response.status === 204) {
            return null;
        }
        return await response.json();
    }

    let errorMessage = 'Something went wrong.';
    try {
        const errorBody = await response.json();
        if (typeof errorBody === 'string') {
            errorMessage = errorBody;
        } else if (errorBody.message) {
            errorMessage = errorBody.message;
        } else {
            errorMessage = Object.values(errorBody).join(' ');
        }
    } catch (error) {
        // ignore parsing errors
    }
    throw new Error(errorMessage);
}

async function loadShipments() {
    clearFeedback();
    try {
        const shipments = await fetch(API_BASE).then(handleResponse);
        renderShipments(shipments);
    } catch (error) {
        showFeedback(createFeedback, error.message, 'error');
    }
}

function renderShipments(shipments) {
    shipmentsTableBody.innerHTML = '';
    if (!shipments || shipments.length === 0) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.colSpan = 5;
        cell.textContent = 'No shipments yet. Create one to get started!';
        cell.style.textAlign = 'center';
        row.appendChild(cell);
        shipmentsTableBody.appendChild(row);
        timelineSection.hidden = true;
        return;
    }

    shipments
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
        .forEach(shipment => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${shipment.trackingNumber}</td>
                <td>${shipment.recipientName}</td>
                <td>${shipment.destination}</td>
                <td><span class="status-tag">${statusLabel(shipment.currentStatus)}</span></td>
                <td>${formatDate(shipment.createdAt)}</td>
            `;
            row.addEventListener('click', () => loadShipmentDetails(shipment.trackingNumber));
            shipmentsTableBody.appendChild(row);
        });
}

async function loadShipmentDetails(trackingNumber) {
    clearFeedback();
    try {
        const shipment = await fetch(`${API_BASE}/${trackingNumber}`).then(handleResponse);
        renderShipmentTimeline(shipment);
        timelineSection.hidden = false;
        eventTrackingNumberInput.value = shipment.trackingNumber;
        addEventForm.reset();
        showFeedback(eventFeedback, '', '');
    } catch (error) {
        showFeedback(eventFeedback, error.message, 'error');
    }
}

function renderShipmentTimeline(shipment) {
    timelineContainer.innerHTML = '';

    const header = document.createElement('div');
    header.className = 'timeline-header';
    header.innerHTML = `
        <h3>Tracking #${shipment.trackingNumber}</h3>
        <p><strong>Recipient:</strong> ${shipment.recipientName} &bull; <strong>Status:</strong> <span class="status-tag">${statusLabel(shipment.currentStatus)}</span></p>
        <p><strong>Destination:</strong> ${shipment.destination}</p>
    `;
    timelineContainer.appendChild(header);

    if (!shipment.trackingEvents || shipment.trackingEvents.length === 0) {
        const emptyState = document.createElement('p');
        emptyState.textContent = 'No tracking events recorded yet.';
        timelineContainer.appendChild(emptyState);
        return;
    }

    shipment.trackingEvents
        .sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp))
        .forEach(event => {
            const item = document.createElement('div');
            item.className = 'timeline-item';
            item.innerHTML = `
                <h4>${formatDate(event.timestamp)}</h4>
                <p><strong>${event.location}</strong></p>
                <p>${event.description}</p>
                <span class="status-tag">${statusLabel(event.status)}</span>
            `;
            timelineContainer.appendChild(item);
        });
}

createForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const formData = new FormData(createForm);
    const payload = {
        recipientName: formData.get('recipient'),
        destination: formData.get('destination'),
        status: formData.get('status')
    };

    try {
        const shipment = await fetch(API_BASE, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        }).then(handleResponse);

        showFeedback(createFeedback, `Shipment ${shipment.trackingNumber} created successfully.`, 'success');
        createForm.reset();
        await loadShipments();
        await loadShipmentDetails(shipment.trackingNumber);
    } catch (error) {
        showFeedback(createFeedback, error.message, 'error');
    }
});

addEventForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const trackingNumber = eventTrackingNumberInput.value;
    if (!trackingNumber) {
        showFeedback(eventFeedback, 'Select a shipment from the table first.', 'error');
        return;
    }

    const formData = new FormData(addEventForm);
    const payload = {
        location: formData.get('location'),
        description: formData.get('description'),
        status: formData.get('status')
    };

    try {
        await fetch(`${API_BASE}/${trackingNumber}/events`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(payload)
        }).then(handleResponse);

        showFeedback(eventFeedback, 'Tracking event recorded.', 'success');
        addEventForm.reset();
        await loadShipments();
        await loadShipmentDetails(trackingNumber);
    } catch (error) {
        showFeedback(eventFeedback, error.message, 'error');
    }
});

refreshButton.addEventListener('click', () => loadShipments());

loadShipments();
