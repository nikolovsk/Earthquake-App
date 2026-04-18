import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet"
import type { Earthquake } from "../types/earthquake"
import L from "leaflet"

import markerIcon from "leaflet/dist/images/marker-icon.png"
import markerShadow from "leaflet/dist/images/marker-shadow.png"
import defaultIcon from "../utils/defaultIcon.tsx";

L.Icon.Default.mergeOptions({
    iconUrl: markerIcon,
    shadowUrl: markerShadow,
})

interface Props {
    earthquakes: Earthquake[]
}

export default function EarthquakeMap({ earthquakes }: Props) {
    return (
        <div style={{ marginTop: "20px" }}>
            <MapContainer
                center={[20, 0]}
                zoom={2}
                style={{ height: "500px", width: "100%", borderRadius: "12px" }}
            >
                <TileLayer
                    attribution='&copy; OpenStreetMap contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                {earthquakes.map(e => (
                    <Marker
                        key={e.id}
                        position={[e.latitude, e.longitude]}
                        icon={defaultIcon}
                    >
                        <Popup>
                            <strong>{e.title}</strong>
                            <br />
                            Magnitude: {e.magnitude}
                            <br />
                            {e.place}
                        </Popup>
                    </Marker>
                ))}
            </MapContainer>
        </div>
    )
}